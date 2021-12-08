import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TrancheAideEnfantGedComponentsPage,
  TrancheAideEnfantGedDeleteDialog,
  TrancheAideEnfantGedUpdatePage,
} from './tranche-aide-enfant-ged.page-object';

const expect = chai.expect;

describe('TrancheAideEnfantGed e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let trancheAideEnfantGedComponentsPage: TrancheAideEnfantGedComponentsPage;
  let trancheAideEnfantGedUpdatePage: TrancheAideEnfantGedUpdatePage;
  let trancheAideEnfantGedDeleteDialog: TrancheAideEnfantGedDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TrancheAideEnfantGeds', async () => {
    await navBarPage.goToEntity('tranche-aide-enfant-ged');
    trancheAideEnfantGedComponentsPage = new TrancheAideEnfantGedComponentsPage();
    await browser.wait(ec.visibilityOf(trancheAideEnfantGedComponentsPage.title), 5000);
    expect(await trancheAideEnfantGedComponentsPage.getTitle()).to.eq('bcaApp.trancheAideEnfantGed.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(trancheAideEnfantGedComponentsPage.entities), ec.visibilityOf(trancheAideEnfantGedComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TrancheAideEnfantGed page', async () => {
    await trancheAideEnfantGedComponentsPage.clickOnCreateButton();
    trancheAideEnfantGedUpdatePage = new TrancheAideEnfantGedUpdatePage();
    expect(await trancheAideEnfantGedUpdatePage.getPageTitle()).to.eq('bcaApp.trancheAideEnfantGed.home.createOrEditLabel');
    await trancheAideEnfantGedUpdatePage.cancel();
  });

  it('should create and save TrancheAideEnfantGeds', async () => {
    const nbButtonsBeforeCreate = await trancheAideEnfantGedComponentsPage.countDeleteButtons();

    await trancheAideEnfantGedComponentsPage.clickOnCreateButton();

    await promise.all([
      trancheAideEnfantGedUpdatePage.setAnneInput('5'),
      trancheAideEnfantGedUpdatePage.setMoisInput('5'),
      trancheAideEnfantGedUpdatePage.setAgeEnfantRevoluSurPeriodeInput('5'),
      trancheAideEnfantGedUpdatePage.setMontantPlafondSalaireInput('5'),
      trancheAideEnfantGedUpdatePage.getIsActifInput().click(),
      trancheAideEnfantGedUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      trancheAideEnfantGedUpdatePage.getIsUpdatedInput().click(),
      trancheAideEnfantGedUpdatePage.setDateModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      trancheAideEnfantGedUpdatePage.strategieCmgGedSelectLastOption(),
    ]);

    await trancheAideEnfantGedUpdatePage.save();
    expect(await trancheAideEnfantGedUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await trancheAideEnfantGedComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TrancheAideEnfantGed', async () => {
    const nbButtonsBeforeDelete = await trancheAideEnfantGedComponentsPage.countDeleteButtons();
    await trancheAideEnfantGedComponentsPage.clickOnLastDeleteButton();

    trancheAideEnfantGedDeleteDialog = new TrancheAideEnfantGedDeleteDialog();
    expect(await trancheAideEnfantGedDeleteDialog.getDialogTitle()).to.eq('bcaApp.trancheAideEnfantGed.delete.question');
    await trancheAideEnfantGedDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(trancheAideEnfantGedComponentsPage.title), 5000);

    expect(await trancheAideEnfantGedComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
