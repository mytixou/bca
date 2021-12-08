import { element, by, ElementFinder } from 'protractor';

export class SoldeApaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-solde-apa div table .btn-danger'));
  title = element.all(by.css('jhi-solde-apa div h2#page-heading span')).first();
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

export class SoldeApaUpdatePage {
  pageTitle = element(by.id('jhi-solde-apa-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  dateInput = element(by.id('field_date'));
  isActifInput = element(by.id('field_isActif'));
  isDernierInput = element(by.id('field_isDernier'));
  anneeInput = element(by.id('field_annee'));
  moisInput = element(by.id('field_mois'));
  consoMontantApaCotisationsInput = element(by.id('field_consoMontantApaCotisations'));
  consoMontantApaSalaireInput = element(by.id('field_consoMontantApaSalaire'));
  soldeMontantApaInput = element(by.id('field_soldeMontantApa'));
  consoHeureApaInput = element(by.id('field_consoHeureApa'));
  soldeHeureApaInput = element(by.id('field_soldeHeureApa'));

  droitsStrategieApaSelect = element(by.id('field_droitsStrategieApa'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setDateInput(date: string): Promise<void> {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput(): Promise<string> {
    return await this.dateInput.getAttribute('value');
  }

  getIsActifInput(): ElementFinder {
    return this.isActifInput;
  }

  getIsDernierInput(): ElementFinder {
    return this.isDernierInput;
  }

  async setAnneeInput(annee: string): Promise<void> {
    await this.anneeInput.sendKeys(annee);
  }

  async getAnneeInput(): Promise<string> {
    return await this.anneeInput.getAttribute('value');
  }

  async setMoisInput(mois: string): Promise<void> {
    await this.moisInput.sendKeys(mois);
  }

  async getMoisInput(): Promise<string> {
    return await this.moisInput.getAttribute('value');
  }

  async setConsoMontantApaCotisationsInput(consoMontantApaCotisations: string): Promise<void> {
    await this.consoMontantApaCotisationsInput.sendKeys(consoMontantApaCotisations);
  }

  async getConsoMontantApaCotisationsInput(): Promise<string> {
    return await this.consoMontantApaCotisationsInput.getAttribute('value');
  }

  async setConsoMontantApaSalaireInput(consoMontantApaSalaire: string): Promise<void> {
    await this.consoMontantApaSalaireInput.sendKeys(consoMontantApaSalaire);
  }

  async getConsoMontantApaSalaireInput(): Promise<string> {
    return await this.consoMontantApaSalaireInput.getAttribute('value');
  }

  async setSoldeMontantApaInput(soldeMontantApa: string): Promise<void> {
    await this.soldeMontantApaInput.sendKeys(soldeMontantApa);
  }

  async getSoldeMontantApaInput(): Promise<string> {
    return await this.soldeMontantApaInput.getAttribute('value');
  }

  async setConsoHeureApaInput(consoHeureApa: string): Promise<void> {
    await this.consoHeureApaInput.sendKeys(consoHeureApa);
  }

  async getConsoHeureApaInput(): Promise<string> {
    return await this.consoHeureApaInput.getAttribute('value');
  }

  async setSoldeHeureApaInput(soldeHeureApa: string): Promise<void> {
    await this.soldeHeureApaInput.sendKeys(soldeHeureApa);
  }

  async getSoldeHeureApaInput(): Promise<string> {
    return await this.soldeHeureApaInput.getAttribute('value');
  }

  async droitsStrategieApaSelectLastOption(): Promise<void> {
    await this.droitsStrategieApaSelect.all(by.tagName('option')).last().click();
  }

  async droitsStrategieApaSelectOption(option: string): Promise<void> {
    await this.droitsStrategieApaSelect.sendKeys(option);
  }

  getDroitsStrategieApaSelect(): ElementFinder {
    return this.droitsStrategieApaSelect;
  }

  async getDroitsStrategieApaSelectedOption(): Promise<string> {
    return await this.droitsStrategieApaSelect.element(by.css('option:checked')).getText();
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

export class SoldeApaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-soldeApa-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-soldeApa'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
