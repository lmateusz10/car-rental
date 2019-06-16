import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategory } from 'app/shared/model/category.model';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ICar } from 'app/shared/model/car.model';
import { CarService } from 'app/entities/car';

@Component({
    selector: 'jhi-category-detail',
    templateUrl: './category-detail.component.html'
})
export class CategoryDetailComponent implements OnInit {
    category: ICategory;
    cars: ICar[] = [];

    constructor(protected activatedRoute: ActivatedRoute, protected carService: CarService) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ category }) => {
            this.category = category;
            this.cars = this.category.cars;
        });

        // this.carService
        //     .query({
        //         page: 0,
        //         size: 100000,
        //         sort: ['id' + ',' + (true ? 'asc' : 'desc')]
        //     })
        //     .subscribe(
        //         (res: HttpResponse<ICar[]>) => {
        //
        //             var idList = []
        //             res.body.forEach(x => {
        //                 idList.push(x)
        //             })
        //
        //
        //
        //
        //
        //         }
        //     );
    }

    previousState() {
        window.history.back();
    }
}
