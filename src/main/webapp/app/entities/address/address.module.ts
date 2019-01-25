import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CarRentalSystemSharedModule } from 'app/shared';
import { CarRentalSystemAdminModule } from 'app/admin/admin.module';
import {
    AddressComponent,
    AddressDetailComponent,
    AddressUpdateComponent,
    AddressDeletePopupComponent,
    AddressDeleteDialogComponent,
    addressRoute,
    addressPopupRoute
} from './';

const ENTITY_STATES = [...addressRoute, ...addressPopupRoute];

@NgModule({
    imports: [CarRentalSystemSharedModule, CarRentalSystemAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AddressComponent,
        AddressDetailComponent,
        AddressUpdateComponent,
        AddressDeleteDialogComponent,
        AddressDeletePopupComponent
    ],
    entryComponents: [AddressComponent, AddressUpdateComponent, AddressDeleteDialogComponent, AddressDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarRentalSystemAddressModule {}
