import { element, by, ElementFinder } from 'protractor';

export class StrategieCiComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-strategie-ci div table .btn-danger'));
  title = element.all(by.css('jhi-strategie-ci div h2#page-heading span')).first();
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

export class StrategieCiUpdatePage {
  pageTitle = element(by.id('jhi-strategie-ci-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  isActifInput = element(by.id('field_isActif'));
  dateAnnuelleDebutValiditeInput = element(by.id('field_dateAnnuelleDebutValidite'));
  anneInput = element(by.id('field_anne'));
  montantPlafondDefautInput = element(by.id('field_montantPlafondDefaut'));
  montantPlafondHandicapeInput = element(by.id('field_montantPlafondHandicape'));
  montantPlafondDefautPlusInput = element(by.id('field_montantPlafondDefautPlus'));
  montantPlafondHandicapePlusInput = element(by.id('field_montantPlafondHandicapePlus'));
  tauxSalaireInput = element(by.id('field_tauxSalaire'));

  aideSelect = element(by.id('field_aide'));
  tiersFinanceurSelect = element(by.id('field_tiersFinanceur'));
  natureActiviteSelect = element(by.id('field_natureActivite'));
  natureMontantSelect = element(by.id('field_natureMontant'));

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

  async setDateAnnuelleDebutValiditeInput(dateAnnuelleDebutValidite: string): Promise<void> {
    await this.dateAnnuelleDebutValiditeInput.sendKeys(dateAnnuelleDebutValidite);
  }

  async getDateAnnuelleDebutValiditeInput(): Promise<string> {
    return await this.dateAnnuelleDebutValiditeInput.getAttribute('value');
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

  async aideSelectLastOption(): Promise<void> {
    await this.aideSelect.all(by.tagName('option')).last().click();
  }

  async aideSelectOption(option: string): Promise<void> {
    await this.aideSelect.sendKeys(option);
  }

  getAideSelect(): ElementFinder {
    return this.aideSelect;
  }

  async getAideSelectedOption(): Promise<string> {
    return await this.aideSelect.element(by.css('option:checked')).getText();
  }

  async tiersFinanceurSelectLastOption(): Promise<void> {
    await this.tiersFinanceurSelect.all(by.tagName('option')).last().click();
  }

  async tiersFinanceurSelectOption(option: string): Promise<void> {
    await this.tiersFinanceurSelect.sendKeys(option);
  }

  getTiersFinanceurSelect(): ElementFinder {
    return this.tiersFinanceurSelect;
  }

  async getTiersFinanceurSelectedOption(): Promise<string> {
    return await this.tiersFinanceurSelect.element(by.css('option:checked')).getText();
  }

  async natureActiviteSelectLastOption(): Promise<void> {
    await this.natureActiviteSelect.all(by.tagName('option')).last().click();
  }

  async natureActiviteSelectOption(option: string): Promise<void> {
    await this.natureActiviteSelect.sendKeys(option);
  }

  getNatureActiviteSelect(): ElementFinder {
    return this.natureActiviteSelect;
  }

  async getNatureActiviteSelectedOption(): Promise<string> {
    return await this.natureActiviteSelect.element(by.css('option:checked')).getText();
  }

  async natureMontantSelectLastOption(): Promise<void> {
    await this.natureMontantSelect.all(by.tagName('option')).last().click();
  }

  async natureMontantSelectOption(option: string): Promise<void> {
    await this.natureMontantSelect.sendKeys(option);
  }

  getNatureMontantSelect(): ElementFinder {
    return this.natureMontantSelect;
  }

  async getNatureMontantSelectedOption(): Promise<string> {
    return await this.natureMontantSelect.element(by.css('option:checked')).getText();
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

export class StrategieCiDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-strategieCi-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-strategieCi'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
