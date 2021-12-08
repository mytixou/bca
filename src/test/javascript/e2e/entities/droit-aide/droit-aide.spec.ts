import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DroitAideComponentsPage, DroitAideDeleteDialog, DroitAideUpdatePage } from './droit-aide.page-object';

const expect = chai.expect;

describe('DroitAide e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let droitAideComponentsPage: DroitAideComponentsPage;
  let droitAideUpdatePage: DroitAideUpdatePage;
  let droitAideDeleteDialog: DroitAideDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DroitAides', async () => {
    await navBarPage.goToEntity('droit-aide');
    droitAideComponentsPage = new DroitAideComponentsPage();
    await browser.wait(ec.visibilityOf(droitAideComponentsPage.title), 5000);
    expect(await droitAideComponentsPage.getTitle()).to.eq('bcaApp.droitAide.home.title');
    await browser.wait(ec.or(ec.visibilityOf(droitAideComponentsPage.entities), ec.visibilityOf(droitAideComponentsPage.noResult)), 1000);
  });

  it('should load create DroitAide page', async () => {
    await droitAideComponentsPage.clickOnCreateButton();
    droitAideUpdatePage = new DroitAideUpdatePage();
    expect(await droitAideUpdatePage.getPageTitle()).to.eq('bcaApp.droitAide.home.createOrEditLabel');
    await droitAideUpdatePage.cancel();
  });

  it('should create and save DroitAides', async () => {
    const nbButtonsBeforeCreate = await droitAideComponentsPage.countDeleteButtons();

    await droitAideComponentsPage.clickOnCreateButton();

    await promise.all([
      droitAideUpdatePage.getIsActifInput().click(),
      droitAideUpdatePage.setAnneInput('5'),
      droitAideUpdatePage.setDateOuvertureInput('2000-12-31'),
      droitAideUpdatePage.setDateFermetureInput('2000-12-31'),
      droitAideUpdatePage.produitSelectLastOption(),
      droitAideUpdatePage.produitSelectLastOption(),
    ]);

    await droitAideUpdatePage.save();
    expect(await droitAideUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await droitAideComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DroitAide', async () => {
    const nbButtonsBeforeDelete = await droitAideComponentsPage.countDeleteButtons();
    await droitAideComponentsPage.clickOnLastDeleteButton();

    droitAideDeleteDialog = new DroitAideDeleteDialog();
    expect(await droitAideDeleteDialog.getDialogTitle()).to.eq('bcaApp.droitAide.delete.question');
    await droitAideDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(droitAideComponentsPage.title), 5000);

    expect(await droitAideComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
