import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StrategieCmgGedComponentsPage, StrategieCmgGedDeleteDialog, StrategieCmgGedUpdatePage } from './strategie-cmg-ged.page-object';

const expect = chai.expect;

describe('StrategieCmgGed e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let strategieCmgGedComponentsPage: StrategieCmgGedComponentsPage;
  let strategieCmgGedUpdatePage: StrategieCmgGedUpdatePage;
  let strategieCmgGedDeleteDialog: StrategieCmgGedDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StrategieCmgGeds', async () => {
    await navBarPage.goToEntity('strategie-cmg-ged');
    strategieCmgGedComponentsPage = new StrategieCmgGedComponentsPage();
    await browser.wait(ec.visibilityOf(strategieCmgGedComponentsPage.title), 5000);
    expect(await strategieCmgGedComponentsPage.getTitle()).to.eq('bcaApp.strategieCmgGed.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(strategieCmgGedComponentsPage.entities), ec.visibilityOf(strategieCmgGedComponentsPage.noResult)),
      1000
    );
  });

  it('should load create StrategieCmgGed page', async () => {
    await strategieCmgGedComponentsPage.clickOnCreateButton();
    strategieCmgGedUpdatePage = new StrategieCmgGedUpdatePage();
    expect(await strategieCmgGedUpdatePage.getPageTitle()).to.eq('bcaApp.strategieCmgGed.home.createOrEditLabel');
    await strategieCmgGedUpdatePage.cancel();
  });

  it('should create and save StrategieCmgGeds', async () => {
    const nbButtonsBeforeCreate = await strategieCmgGedComponentsPage.countDeleteButtons();

    await strategieCmgGedComponentsPage.clickOnCreateButton();

    await promise.all([
      strategieCmgGedUpdatePage.setAnneInput('5'),
      strategieCmgGedUpdatePage.setMoisInput('5'),
      strategieCmgGedUpdatePage.setNbHeureSeuilPlafondInput('5'),
      strategieCmgGedUpdatePage.setTauxSalaireInput('5'),
      strategieCmgGedUpdatePage.setTauxCotisationsInput('5'),
      strategieCmgGedUpdatePage.getIsActifInput().click(),
      strategieCmgGedUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      strategieCmgGedUpdatePage.getIsUpdatedInput().click(),
      strategieCmgGedUpdatePage.setDateModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      strategieCmgGedUpdatePage.aideSelectLastOption(),
      strategieCmgGedUpdatePage.tiersFinanceurSelectLastOption(),
    ]);

    await strategieCmgGedUpdatePage.save();
    expect(await strategieCmgGedUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await strategieCmgGedComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last StrategieCmgGed', async () => {
    const nbButtonsBeforeDelete = await strategieCmgGedComponentsPage.countDeleteButtons();
    await strategieCmgGedComponentsPage.clickOnLastDeleteButton();

    strategieCmgGedDeleteDialog = new StrategieCmgGedDeleteDialog();
    expect(await strategieCmgGedDeleteDialog.getDialogTitle()).to.eq('bcaApp.strategieCmgGed.delete.question');
    await strategieCmgGedDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(strategieCmgGedComponentsPage.title), 5000);

    expect(await strategieCmgGedComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
