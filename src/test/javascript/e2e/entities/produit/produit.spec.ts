import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProduitComponentsPage, ProduitDeleteDialog, ProduitUpdatePage } from './produit.page-object';

const expect = chai.expect;

describe('Produit e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let produitComponentsPage: ProduitComponentsPage;
  let produitUpdatePage: ProduitUpdatePage;
  let produitDeleteDialog: ProduitDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Produits', async () => {
    await navBarPage.goToEntity('produit');
    produitComponentsPage = new ProduitComponentsPage();
    await browser.wait(ec.visibilityOf(produitComponentsPage.title), 5000);
    expect(await produitComponentsPage.getTitle()).to.eq('bcaApp.produit.home.title');
    await browser.wait(ec.or(ec.visibilityOf(produitComponentsPage.entities), ec.visibilityOf(produitComponentsPage.noResult)), 1000);
  });

  it('should load create Produit page', async () => {
    await produitComponentsPage.clickOnCreateButton();
    produitUpdatePage = new ProduitUpdatePage();
    expect(await produitUpdatePage.getPageTitle()).to.eq('bcaApp.produit.home.createOrEditLabel');
    await produitUpdatePage.cancel();
  });

  it('should create and save Produits', async () => {
    const nbButtonsBeforeCreate = await produitComponentsPage.countDeleteButtons();

    await produitComponentsPage.clickOnCreateButton();

    await promise.all([
      produitUpdatePage.nomSelectLastOption(),
      produitUpdatePage.getIsActifInput().click(),
      produitUpdatePage.setDateLancementInput('2000-12-31'),
      produitUpdatePage.setAnneLancementInput('5'),
      produitUpdatePage.setMoisLancementInput('5'),
      produitUpdatePage.setDateResiliationInput('2000-12-31'),
      produitUpdatePage.setDerniereAnneeInput('5'),
      produitUpdatePage.setDernierMoisInput('5'),
    ]);

    await produitUpdatePage.save();
    expect(await produitUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await produitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Produit', async () => {
    const nbButtonsBeforeDelete = await produitComponentsPage.countDeleteButtons();
    await produitComponentsPage.clickOnLastDeleteButton();

    produitDeleteDialog = new ProduitDeleteDialog();
    expect(await produitDeleteDialog.getDialogTitle()).to.eq('bcaApp.produit.delete.question');
    await produitDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(produitComponentsPage.title), 5000);

    expect(await produitComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
