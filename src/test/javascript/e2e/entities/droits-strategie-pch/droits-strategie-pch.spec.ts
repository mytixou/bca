import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DroitsStrategiePchComponentsPage,
  DroitsStrategiePchDeleteDialog,
  DroitsStrategiePchUpdatePage,
} from './droits-strategie-pch.page-object';

const expect = chai.expect;

describe('DroitsStrategiePch e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let droitsStrategiePchComponentsPage: DroitsStrategiePchComponentsPage;
  let droitsStrategiePchUpdatePage: DroitsStrategiePchUpdatePage;
  let droitsStrategiePchDeleteDialog: DroitsStrategiePchDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DroitsStrategiePches', async () => {
    await navBarPage.goToEntity('droits-strategie-pch');
    droitsStrategiePchComponentsPage = new DroitsStrategiePchComponentsPage();
    await browser.wait(ec.visibilityOf(droitsStrategiePchComponentsPage.title), 5000);
    expect(await droitsStrategiePchComponentsPage.getTitle()).to.eq('bcaApp.droitsStrategiePch.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(droitsStrategiePchComponentsPage.entities), ec.visibilityOf(droitsStrategiePchComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DroitsStrategiePch page', async () => {
    await droitsStrategiePchComponentsPage.clickOnCreateButton();
    droitsStrategiePchUpdatePage = new DroitsStrategiePchUpdatePage();
    expect(await droitsStrategiePchUpdatePage.getPageTitle()).to.eq('bcaApp.droitsStrategiePch.home.createOrEditLabel');
    await droitsStrategiePchUpdatePage.cancel();
  });

  it('should create and save DroitsStrategiePches', async () => {
    const nbButtonsBeforeCreate = await droitsStrategiePchComponentsPage.countDeleteButtons();

    await droitsStrategiePchComponentsPage.clickOnCreateButton();

    await promise.all([
      droitsStrategiePchUpdatePage.getIsActifInput().click(),
      droitsStrategiePchUpdatePage.setAnneInput('5'),
      droitsStrategiePchUpdatePage.setMoisInput('5'),
      droitsStrategiePchUpdatePage.setMontantPlafondInput('5'),
      droitsStrategiePchUpdatePage.setMontantPlafondPlusInput('5'),
      droitsStrategiePchUpdatePage.setNbHeurePlafondInput('5'),
      droitsStrategiePchUpdatePage.setTauxCotisationsInput('5'),
      droitsStrategiePchUpdatePage.setDateOuvertureInput('2000-12-31'),
      droitsStrategiePchUpdatePage.setDateFermetureInput('2000-12-31'),
      droitsStrategiePchUpdatePage.beneficiaireSelectLastOption(),
    ]);

    await droitsStrategiePchUpdatePage.save();
    expect(await droitsStrategiePchUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await droitsStrategiePchComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DroitsStrategiePch', async () => {
    const nbButtonsBeforeDelete = await droitsStrategiePchComponentsPage.countDeleteButtons();
    await droitsStrategiePchComponentsPage.clickOnLastDeleteButton();

    droitsStrategiePchDeleteDialog = new DroitsStrategiePchDeleteDialog();
    expect(await droitsStrategiePchDeleteDialog.getDialogTitle()).to.eq('bcaApp.droitsStrategiePch.delete.question');
    await droitsStrategiePchDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(droitsStrategiePchComponentsPage.title), 5000);

    expect(await droitsStrategiePchComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
