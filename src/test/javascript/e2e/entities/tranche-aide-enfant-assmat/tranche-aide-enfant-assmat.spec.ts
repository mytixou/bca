import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TrancheAideEnfantAssmatComponentsPage,
  TrancheAideEnfantAssmatDeleteDialog,
  TrancheAideEnfantAssmatUpdatePage,
} from './tranche-aide-enfant-assmat.page-object';

const expect = chai.expect;

describe('TrancheAideEnfantAssmat e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let trancheAideEnfantAssmatComponentsPage: TrancheAideEnfantAssmatComponentsPage;
  let trancheAideEnfantAssmatUpdatePage: TrancheAideEnfantAssmatUpdatePage;
  let trancheAideEnfantAssmatDeleteDialog: TrancheAideEnfantAssmatDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TrancheAideEnfantAssmats', async () => {
    await navBarPage.goToEntity('tranche-aide-enfant-assmat');
    trancheAideEnfantAssmatComponentsPage = new TrancheAideEnfantAssmatComponentsPage();
    await browser.wait(ec.visibilityOf(trancheAideEnfantAssmatComponentsPage.title), 5000);
    expect(await trancheAideEnfantAssmatComponentsPage.getTitle()).to.eq('bcaApp.trancheAideEnfantAssmat.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(trancheAideEnfantAssmatComponentsPage.entities),
        ec.visibilityOf(trancheAideEnfantAssmatComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create TrancheAideEnfantAssmat page', async () => {
    await trancheAideEnfantAssmatComponentsPage.clickOnCreateButton();
    trancheAideEnfantAssmatUpdatePage = new TrancheAideEnfantAssmatUpdatePage();
    expect(await trancheAideEnfantAssmatUpdatePage.getPageTitle()).to.eq('bcaApp.trancheAideEnfantAssmat.home.createOrEditLabel');
    await trancheAideEnfantAssmatUpdatePage.cancel();
  });

  it('should create and save TrancheAideEnfantAssmats', async () => {
    const nbButtonsBeforeCreate = await trancheAideEnfantAssmatComponentsPage.countDeleteButtons();

    await trancheAideEnfantAssmatComponentsPage.clickOnCreateButton();

    await promise.all([
      trancheAideEnfantAssmatUpdatePage.setAnneInput('5'),
      trancheAideEnfantAssmatUpdatePage.setMoisInput('5'),
      trancheAideEnfantAssmatUpdatePage.setAgeEnfantRevoluSurPeriodeInput('5'),
      trancheAideEnfantAssmatUpdatePage.setMontantPlafondSalaireInput('5'),
      trancheAideEnfantAssmatUpdatePage.getIsActifInput().click(),
      trancheAideEnfantAssmatUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      trancheAideEnfantAssmatUpdatePage.getIsUpdatedInput().click(),
      trancheAideEnfantAssmatUpdatePage.setDateModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      trancheAideEnfantAssmatUpdatePage.strategieCmgAssmatSelectLastOption(),
    ]);

    await trancheAideEnfantAssmatUpdatePage.save();
    expect(await trancheAideEnfantAssmatUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await trancheAideEnfantAssmatComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TrancheAideEnfantAssmat', async () => {
    const nbButtonsBeforeDelete = await trancheAideEnfantAssmatComponentsPage.countDeleteButtons();
    await trancheAideEnfantAssmatComponentsPage.clickOnLastDeleteButton();

    trancheAideEnfantAssmatDeleteDialog = new TrancheAideEnfantAssmatDeleteDialog();
    expect(await trancheAideEnfantAssmatDeleteDialog.getDialogTitle()).to.eq('bcaApp.trancheAideEnfantAssmat.delete.question');
    await trancheAideEnfantAssmatDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(trancheAideEnfantAssmatComponentsPage.title), 5000);

    expect(await trancheAideEnfantAssmatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
