import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  StrategieCmgAssmatComponentsPage,
  StrategieCmgAssmatDeleteDialog,
  StrategieCmgAssmatUpdatePage,
} from './strategie-cmg-assmat.page-object';

const expect = chai.expect;

describe('StrategieCmgAssmat e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let strategieCmgAssmatComponentsPage: StrategieCmgAssmatComponentsPage;
  let strategieCmgAssmatUpdatePage: StrategieCmgAssmatUpdatePage;
  let strategieCmgAssmatDeleteDialog: StrategieCmgAssmatDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StrategieCmgAssmats', async () => {
    await navBarPage.goToEntity('strategie-cmg-assmat');
    strategieCmgAssmatComponentsPage = new StrategieCmgAssmatComponentsPage();
    await browser.wait(ec.visibilityOf(strategieCmgAssmatComponentsPage.title), 5000);
    expect(await strategieCmgAssmatComponentsPage.getTitle()).to.eq('bcaApp.strategieCmgAssmat.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(strategieCmgAssmatComponentsPage.entities), ec.visibilityOf(strategieCmgAssmatComponentsPage.noResult)),
      1000
    );
  });

  it('should load create StrategieCmgAssmat page', async () => {
    await strategieCmgAssmatComponentsPage.clickOnCreateButton();
    strategieCmgAssmatUpdatePage = new StrategieCmgAssmatUpdatePage();
    expect(await strategieCmgAssmatUpdatePage.getPageTitle()).to.eq('bcaApp.strategieCmgAssmat.home.createOrEditLabel');
    await strategieCmgAssmatUpdatePage.cancel();
  });

  it('should create and save StrategieCmgAssmats', async () => {
    const nbButtonsBeforeCreate = await strategieCmgAssmatComponentsPage.countDeleteButtons();

    await strategieCmgAssmatComponentsPage.clickOnCreateButton();

    await promise.all([
      strategieCmgAssmatUpdatePage.setAnneInput('5'),
      strategieCmgAssmatUpdatePage.setMoisInput('5'),
      strategieCmgAssmatUpdatePage.setNbHeureSeuilPlafondInput('5'),
      strategieCmgAssmatUpdatePage.setTauxSalaireInput('5'),
      strategieCmgAssmatUpdatePage.setTauxCotisationsInput('5'),
      strategieCmgAssmatUpdatePage.getIsActifInput().click(),
      strategieCmgAssmatUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      strategieCmgAssmatUpdatePage.getIsUpdatedInput().click(),
      strategieCmgAssmatUpdatePage.setDateModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      strategieCmgAssmatUpdatePage.aideSelectLastOption(),
      strategieCmgAssmatUpdatePage.tiersFinanceurSelectLastOption(),
    ]);

    await strategieCmgAssmatUpdatePage.save();
    expect(await strategieCmgAssmatUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await strategieCmgAssmatComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last StrategieCmgAssmat', async () => {
    const nbButtonsBeforeDelete = await strategieCmgAssmatComponentsPage.countDeleteButtons();
    await strategieCmgAssmatComponentsPage.clickOnLastDeleteButton();

    strategieCmgAssmatDeleteDialog = new StrategieCmgAssmatDeleteDialog();
    expect(await strategieCmgAssmatDeleteDialog.getDialogTitle()).to.eq('bcaApp.strategieCmgAssmat.delete.question');
    await strategieCmgAssmatDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(strategieCmgAssmatComponentsPage.title), 5000);

    expect(await strategieCmgAssmatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
