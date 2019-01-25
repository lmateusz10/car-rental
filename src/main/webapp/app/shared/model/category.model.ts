import { Moment } from 'moment';
import { ICategory } from 'app/shared/model//category.model';
import { ICar } from 'app/shared/model//car.model';

export const enum CategoryStatus {
    AVAILABLE = 'AVAILABLE',
    RESTRICTED = 'RESTRICTED',
    DISABLED = 'DISABLED'
}

export interface ICategory {
    id?: number;
    description?: string;
    sortOrder?: number;
    dateAdded?: Moment;
    dateModified?: Moment;
    status?: CategoryStatus;
    parent?: ICategory;
    cars?: ICar[];
}

export class Category implements ICategory {
    constructor(
        public id?: number,
        public description?: string,
        public sortOrder?: number,
        public dateAdded?: Moment,
        public dateModified?: Moment,
        public status?: CategoryStatus,
        public parent?: ICategory,
        public cars?: ICar[]
    ) {}
}
