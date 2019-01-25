import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { CarRentalSystemCategoryModule } from './category/category.module';
import { CarRentalSystemCarModule } from './car/car.module';
import { CarRentalSystemAddressModule } from './address/address.module';
import { CarRentalSystemReservationModule } from './reservation/reservation.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        CarRentalSystemCategoryModule,
        CarRentalSystemCarModule,
        CarRentalSystemAddressModule,
        CarRentalSystemReservationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CarRentalSystemEntityModule {}
