import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DroitsStrategieCiComponentsPage,
  DroitsStrategieCiDeleteDialog,
  DroitsStrategieCiUpdatePage,
} from './droits-strategie-ci.page-object';

const expect = chai.expect;

describe('DroitsStrategieCi e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let droitsStrategieCiComponentsPage: DroitsStrategieCiComponentsPage;
  let droitsStrategieCiUpdatePage: DroitsStrategieCiUpdatePage;
  let droitsStrategieCiDeleteDialog: DroitsStrategieCiDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DroitsStrategieCis', async () => {
    await navBarPage.goToEntity('droits-strategie-ci');
    droitsStrategieCiComponentsPage = new DroitsStrategieCiComponentsPage();
    await browser.wait(ec.visibilityOf(droitsStrategieCiComponentsPage.title), 5000);
    expect(await droitsStrategieCiComponentsPage.getTitle()).to.eq('bcaApp.droitsStrategieCi.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(droitsStrategieCiComponentsPage.entities), ec.visibilityOf(droitsStrategieCiComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DroitsStrategieCi page', async () => {
    await droitsStrategieCiComponentsPage.clickOnCreateButton();
    droitsStrategieCiUpdatePage = new DroitsStrategieCiUpdatePage();
    expect(await droitsStrategieCiUpdatePage.getPageTitle()).to.eq('bcaApp.droitsStrategieCi.home.createOrEditLabel');
    await droitsStrategieCiUpdatePage.cancel();
  });

  it('should create and save DroitsStrategieCis', async () => {
    const nbButtonsBeforeCreate = await droitsStrategieCiComponentsPage.countDeleteButtons();

    await droitsStrategieCiComponentsPage.clickOnCreateButton();

    await promise.all([
      droitsStrategieCiUpdatePage.getIsActifInput().click(),
      droitsStrategieCiUpdatePage.setAnneInput('5'),
      droitsStrategieCiUpdatePage.setMontantPlafondDefautInput('5'),
      droitsStrategieCiUpdatePage.setMontantPlafondHandicapeInput('5'),
      droitsStrategieCiUpdatePage.setMontantPlafondDefautPlusInput('5'),
      droitsStrategieCiUpdatePage.setMontantPlafondHandicapePlusInput('5'),
      droitsStrategieCiUpdatePage.setTauxSalaireInput('5'),
      droitsStrategieCiUpdatePage.setDateOuvertureInput('2000-12-31'),
      droitsStrategieCiUpdatePage.setDateFermetureInput('2000-12-31'),
      droitsStrategieCiUpdatePage.beneficiaireSelectLastOption(),
    ]);

    await droitsStrategieCiUpdatePage.save();
    expect(await droitsStrategieCiUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await droitsStrategieCiComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DroitsStrategieCi', async () => {
    const nbButtonsBeforeDelete = await droitsStrategieCiComponentsPage.countDeleteButtons();
    await droitsStrategieCiComponentsPage.clickOnLastDeleteButton();

    droitsStrategieCiDeleteDialog = new DroitsStrategieCiDeleteDialog();
    expect(await droitsStrategieCiDeleteDialog.getDialogTitle()).to.eq('bcaApp.droitsStrategieCi.delete.question');
    await droitsStrategieCiDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(droitsStrategieCiComponentsPage.title), 5000);

    expect(await droitsStrategieCiComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
