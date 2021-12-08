import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DroitsStrategiePchEComponentsPage,
  DroitsStrategiePchEDeleteDialog,
  DroitsStrategiePchEUpdatePage,
} from './droits-strategie-pch-e.page-object';

const expect = chai.expect;

describe('DroitsStrategiePchE e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let droitsStrategiePchEComponentsPage: DroitsStrategiePchEComponentsPage;
  let droitsStrategiePchEUpdatePage: DroitsStrategiePchEUpdatePage;
  let droitsStrategiePchEDeleteDialog: DroitsStrategiePchEDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DroitsStrategiePchES', async () => {
    await navBarPage.goToEntity('droits-strategie-pch-e');
    droitsStrategiePchEComponentsPage = new DroitsStrategiePchEComponentsPage();
    await browser.wait(ec.visibilityOf(droitsStrategiePchEComponentsPage.title), 5000);
    expect(await droitsStrategiePchEComponentsPage.getTitle()).to.eq('bcaApp.droitsStrategiePchE.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(droitsStrategiePchEComponentsPage.entities), ec.visibilityOf(droitsStrategiePchEComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DroitsStrategiePchE page', async () => {
    await droitsStrategiePchEComponentsPage.clickOnCreateButton();
    droitsStrategiePchEUpdatePage = new DroitsStrategiePchEUpdatePage();
    expect(await droitsStrategiePchEUpdatePage.getPageTitle()).to.eq('bcaApp.droitsStrategiePchE.home.createOrEditLabel');
    await droitsStrategiePchEUpdatePage.cancel();
  });

  it('should create and save DroitsStrategiePchES', async () => {
    const nbButtonsBeforeCreate = await droitsStrategiePchEComponentsPage.countDeleteButtons();

    await droitsStrategiePchEComponentsPage.clickOnCreateButton();

    await promise.all([
      droitsStrategiePchEUpdatePage.getIsActifInput().click(),
      droitsStrategiePchEUpdatePage.setAnneInput('5'),
      droitsStrategiePchEUpdatePage.setMoisInput('5'),
      droitsStrategiePchEUpdatePage.setMontantPlafondInput('5'),
      droitsStrategiePchEUpdatePage.setMontantPlafondPlusInput('5'),
      droitsStrategiePchEUpdatePage.setNbHeurePlafondInput('5'),
      droitsStrategiePchEUpdatePage.setTauxCotisationsInput('5'),
      droitsStrategiePchEUpdatePage.setDateOuvertureInput('2000-12-31'),
      droitsStrategiePchEUpdatePage.setDateFermetureInput('2000-12-31'),
      droitsStrategiePchEUpdatePage.beneficiaireSelectLastOption(),
    ]);

    await droitsStrategiePchEUpdatePage.save();
    expect(await droitsStrategiePchEUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await droitsStrategiePchEComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DroitsStrategiePchE', async () => {
    const nbButtonsBeforeDelete = await droitsStrategiePchEComponentsPage.countDeleteButtons();
    await droitsStrategiePchEComponentsPage.clickOnLastDeleteButton();

    droitsStrategiePchEDeleteDialog = new DroitsStrategiePchEDeleteDialog();
    expect(await droitsStrategiePchEDeleteDialog.getDialogTitle()).to.eq('bcaApp.droitsStrategiePchE.delete.question');
    await droitsStrategiePchEDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(droitsStrategiePchEComponentsPage.title), 5000);

    expect(await droitsStrategiePchEComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
