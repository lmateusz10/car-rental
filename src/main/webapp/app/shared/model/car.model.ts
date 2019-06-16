import { Moment } from 'moment';
import { IReservation } from 'app/shared/model//reservation.model';
import { ICategory } from 'app/shared/model//category.model';

export const enum TransmissionType {
    MANUAL = 'MANUAL',
    AUTOMATIC = 'AUTOMATIC'
}

export interface ICar {
    id?: number;
    licensePlate?: string;
    name?: string;
    transmission?: TransmissionType;
    aircondition?: boolean;
    doors?: number;
    keywords?: string;
    description?: string;
    rating?: number;
    dateAdded?: Moment;
    dateModified?: Moment;
    sortOrder?: number;
    image_1ContentType?: string;
    image_1?: any;
    image_2ContentType?: string;
    image_2?: any;
    cars?: IReservation[];
    categories?: ICategory[];
}

export class Car implements ICar {
    constructor(
        public id?: number,
        public licensePlate?: string,
        public name?: string,
        public transmission?: TransmissionType,
        public aircondition?: boolean,
        public doors?: number,
        public keywords?: string,
        public description?: string,
        public rating?: number,
        public dateAdded?: Moment,
        public dateModified?: Moment,
        public sortOrder?: number,
        public image_1ContentType?: string,
        public image_1?: any,
        public image_2ContentType?: string,
        public image_2?: any,
        public cars?: IReservation[],
        public categories?: ICategory[]
    ) {
        this.aircondition = this.aircondition || false;
    }
}
