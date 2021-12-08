import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  DroitsStrategieApaComponentsPage,
  DroitsStrategieApaDeleteDialog,
  DroitsStrategieApaUpdatePage,
} from './droits-strategie-apa.page-object';

const expect = chai.expect;

describe('DroitsStrategieApa e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let droitsStrategieApaComponentsPage: DroitsStrategieApaComponentsPage;
  let droitsStrategieApaUpdatePage: DroitsStrategieApaUpdatePage;
  let droitsStrategieApaDeleteDialog: DroitsStrategieApaDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DroitsStrategieApas', async () => {
    await navBarPage.goToEntity('droits-strategie-apa');
    droitsStrategieApaComponentsPage = new DroitsStrategieApaComponentsPage();
    await browser.wait(ec.visibilityOf(droitsStrategieApaComponentsPage.title), 5000);
    expect(await droitsStrategieApaComponentsPage.getTitle()).to.eq('bcaApp.droitsStrategieApa.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(droitsStrategieApaComponentsPage.entities), ec.visibilityOf(droitsStrategieApaComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DroitsStrategieApa page', async () => {
    await droitsStrategieApaComponentsPage.clickOnCreateButton();
    droitsStrategieApaUpdatePage = new DroitsStrategieApaUpdatePage();
    expect(await droitsStrategieApaUpdatePage.getPageTitle()).to.eq('bcaApp.droitsStrategieApa.home.createOrEditLabel');
    await droitsStrategieApaUpdatePage.cancel();
  });

  it('should create and save DroitsStrategieApas', async () => {
    const nbButtonsBeforeCreate = await droitsStrategieApaComponentsPage.countDeleteButtons();

    await droitsStrategieApaComponentsPage.clickOnCreateButton();

    await promise.all([
      droitsStrategieApaUpdatePage.getIsActifInput().click(),
      droitsStrategieApaUpdatePage.setAnneInput('5'),
      droitsStrategieApaUpdatePage.setMoisInput('5'),
      droitsStrategieApaUpdatePage.setMontantPlafondInput('5'),
      droitsStrategieApaUpdatePage.setMontantPlafondPlusInput('5'),
      droitsStrategieApaUpdatePage.setNbHeurePlafondInput('5'),
      droitsStrategieApaUpdatePage.setTauxCotisationsInput('5'),
      droitsStrategieApaUpdatePage.setDateOuvertureInput('2000-12-31'),
      droitsStrategieApaUpdatePage.setDateFermetureInput('2000-12-31'),
      droitsStrategieApaUpdatePage.beneficiaireSelectLastOption(),
    ]);

    await droitsStrategieApaUpdatePage.save();
    expect(await droitsStrategieApaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await droitsStrategieApaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last DroitsStrategieApa', async () => {
    const nbButtonsBeforeDelete = await droitsStrategieApaComponentsPage.countDeleteButtons();
    await droitsStrategieApaComponentsPage.clickOnLastDeleteButton();

    droitsStrategieApaDeleteDialog = new DroitsStrategieApaDeleteDialog();
    expect(await droitsStrategieApaDeleteDialog.getDialogTitle()).to.eq('bcaApp.droitsStrategieApa.delete.question');
    await droitsStrategieApaDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(droitsStrategieApaComponentsPage.title), 5000);

    expect(await droitsStrategieApaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
