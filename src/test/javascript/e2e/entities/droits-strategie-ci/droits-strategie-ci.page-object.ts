import { element, by, ElementFinder } from 'protractor';

export class DroitsStrategieCiComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-droits-strategie-ci div table .btn-danger'));
  title = element.all(by.css('jhi-droits-strategie-ci div h2#page-heading span')).first();
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

export class DroitsStrategieCiUpdatePage {
  pageTitle = element(by.id('jhi-droits-strategie-ci-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  isActifInput = element(by.id('field_isActif'));
  anneInput = element(by.id('field_anne'));
  montantPlafondDefautInput = element(by.id('field_montantPlafondDefaut'));
  montantPlafondHandicapeInput = element(by.id('field_montantPlafondHandicape'));
  montantPlafondDefautPlusInput = element(by.id('field_montantPlafondDefautPlus'));
  montantPlafondHandicapePlusInput = element(by.id('field_montantPlafondHandicapePlus'));
  tauxSalaireInput = element(by.id('field_tauxSalaire'));
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

  async setMontantPlafondDefautInput(montantPlafondDefaut: string): Promise<void> {
    await this.montantPlafondDefautInput.sendKeys(montantPlafondDefaut);
  }

  async getMontantPlafondDefautInput(): Promise<string> {
    return await this.montantPlafondDefautInput.getAttribute('value');
  }

  async setMontantPlafondHandicapeInput(montantPlafondHandicape: string): Promise<void> {
    await this.montantPlafondHandicapeInput.sendKeys(montantPlafondHandicape);
  }

  async getMontantPlafondHandicapeInput(): Promise<string> {
    return await this.montantPlafondHandicapeInput.getAttribute('value');
  }

  async setMontantPlafondDefautPlusInput(montantPlafondDefautPlus: string): Promise<void> {
    await this.montantPlafondDefautPlusInput.sendKeys(montantPlafondDefautPlus);
  }

  async getMontantPlafondDefautPlusInput(): Promise<string> {
    return await this.montantPlafondDefautPlusInput.getAttribute('value');
  }

  async setMontantPlafondHandicapePlusInput(montantPlafondHandicapePlus: string): Promise<void> {
    await this.montantPlafondHandicapePlusInput.sendKeys(montantPlafondHandicapePlus);
  }

  async getMontantPlafondHandicapePlusInput(): Promise<string> {
    return await this.montantPlafondHandicapePlusInput.getAttribute('value');
  }

  async setTauxSalaireInput(tauxSalaire: string): Promise<void> {
    await this.tauxSalaireInput.sendKeys(tauxSalaire);
  }

  async getTauxSalaireInput(): Promise<string> {
    return await this.tauxSalaireInput.getAttribute('value');
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

export class DroitsStrategieCiDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-droitsStrategieCi-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-droitsStrategieCi'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
