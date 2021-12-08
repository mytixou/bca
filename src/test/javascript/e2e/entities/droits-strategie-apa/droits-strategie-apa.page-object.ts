import { element, by, ElementFinder } from 'protractor';

export class DroitsStrategieApaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-droits-strategie-apa div table .btn-danger'));
  title = element.all(by.css('jhi-droits-strategie-apa div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class DroitsStrategieApaUpdatePage {
  pageTitle = element(by.id('jhi-droits-strategie-apa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  isActifInput = element(by.id('field_isActif'));
  anneInput = element(by.id('field_anne'));
  moisInput = element(by.id('field_mois'));
  montantPlafondInput = element(by.id('field_montantPlafond'));
  montantPlafondPlusInput = element(by.id('field_montantPlafondPlus'));
  nbHeurePlafondInput = element(by.id('field_nbHeurePlafond'));
  tauxCotisationsInput = element(by.id('field_tauxCotisations'));
  dateOuvertureInput = element(by.id('field_dateOuverture'));
  dateFermetureInput = element(by.id('field_dateFermeture'));

  beneficiaireSelect = element(by.id('field_beneficiaire'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  getIsActifInput(): ElementFinder {
    return this.isActifInput;
  }

  async setAnneInput(anne: string): Promise<void> {
    await this.anneInput.sendKeys(anne);
  }

  async getAnneInput(): Promise<string> {
    return await this.anneInput.getAttribute('value');
  }

  async setMoisInput(mois: string): Promise<void> {
    await this.moisInput.sendKeys(mois);
  }

  async getMoisInput(): Promise<string> {
    return await this.moisInput.getAttribute('value');
  }

  async setMontantPlafondInput(montantPlafond: string): Promise<void> {
    await this.montantPlafondInput.sendKeys(montantPlafond);
  }

  async getMontantPlafondInput(): Promise<string> {
    return await this.montantPlafondInput.getAttribute('value');
  }

  async setMontantPlafondPlusInput(montantPlafondPlus: string): Promise<void> {
    await this.montantPlafondPlusInput.sendKeys(montantPlafondPlus);
  }

  async getMontantPlafondPlusInput(): Promise<string> {
    return await this.montantPlafondPlusInput.getAttribute('value');
  }

  async setNbHeurePlafondInput(nbHeurePlafond: string): Promise<void> {
    await this.nbHeurePlafondInput.sendKeys(nbHeurePlafond);
  }

  async getNbHeurePlafondInput(): Promise<string> {
    return await this.nbHeurePlafondInput.getAttribute('value');
  }

  async setTauxCotisationsInput(tauxCotisations: string): Promise<void> {
    await this.tauxCotisationsInput.sendKeys(tauxCotisations);
  }

  async getTauxCotisationsInput(): Promise<string> {
    return await this.tauxCotisationsInput.getAttribute('value');
  }

  async setDateOuvertureInput(dateOuverture: string): Promise<void> {
    await this.dateOuvertureInput.sendKeys(dateOuverture);
  }

  async getDateOuvertureInput(): Promise<string> {
    return await this.dateOuvertureInput.getAttribute('value');
  }

  async setDateFermetureInput(dateFermeture: string): Promise<void> {
    await this.dateFermetureInput.sendKeys(dateFermeture);
  }

  async getDateFermetureInput(): Promise<string> {
    return await this.dateFermetureInput.getAttribute('value');
  }

  async beneficiaireSelectLastOption(): Promise<void> {
    await this.beneficiaireSelect.all(by.tagName('option')).last().click();
  }

  async beneficiaireSelectOption(option: string): Promise<void> {
    await this.beneficiaireSelect.sendKeys(option);
  }

  getBeneficiaireSelect(): ElementFinder {
    return this.beneficiaireSelect;
  }

  async getBeneficiaireSelectedOption(): Promise<string> {
    return await this.beneficiaireSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class DroitsStrategieApaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-droitsStrategieApa-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-droitsStrategieApa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
