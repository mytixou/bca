import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PecComponentsPage, PecDeleteDialog, PecUpdatePage } from './pec.page-object';

const expect = chai.expect;

describe('Pec e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let pecComponentsPage: PecComponentsPage;
  let pecUpdatePage: PecUpdatePage;
  let pecDeleteDialog: PecDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Pecs', async () => {
    await navBarPage.goToEntity('pec');
    pecComponentsPage = new PecComponentsPage();
    await browser.wait(ec.visibilityOf(pecComponentsPage.title), 5000);
    expect(await pecComponentsPage.getTitle()).to.eq('bcaApp.pec.home.title');
    await browser.wait(ec.or(ec.visibilityOf(pecComponentsPage.entities), ec.visibilityOf(pecComponentsPage.noResult)), 1000);
  });

  it('should load create Pec page', async () => {
    await pecComponentsPage.clickOnCreateButton();
    pecUpdatePage = new PecUpdatePage();
    expect(await pecUpdatePage.getPageTitle()).to.eq('bcaApp.pec.home.createOrEditLabel');
    await pecUpdatePage.cancel();
  });

  it('should create and save Pecs', async () => {
    const nbButtonsBeforeCreate = await pecComponentsPage.countDeleteButtons();

    await pecComponentsPage.clickOnCreateButton();

    await promise.all([
      pecUpdatePage.setIdProduitInput('idProduit'),
      pecUpdatePage.produitSelectLastOption(),
      pecUpdatePage.getIsPlusInput().click(),
      pecUpdatePage.setDateCreatedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      pecUpdatePage.getIsUpdatedInput().click(),
      pecUpdatePage.setDateModifiedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      pecUpdatePage.getIsActifInput().click(),
      pecUpdatePage.setPecDetailsInput('pecDetails'),
      pecUpdatePage.soldeCiSelectLastOption(),
      pecUpdatePage.soldeApaSelectLastOption(),
      pecUpdatePage.soldePchSelectLastOption(),
      pecUpdatePage.soldePchESelectLastOption(),
    ]);

    await pecUpdatePage.save();
    expect(await pecUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await pecComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Pec', async () => {
    const nbButtonsBeforeDelete = await pecComponentsPage.countDeleteButtons();
    await pecComponentsPage.clickOnLastDeleteButton();

    pecDeleteDialog = new PecDeleteDialog();
    expect(await pecDeleteDialog.getDialogTitle()).to.eq('bcaApp.pec.delete.question');
    await pecDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(pecComponentsPage.title), 5000);

    expect(await pecComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
