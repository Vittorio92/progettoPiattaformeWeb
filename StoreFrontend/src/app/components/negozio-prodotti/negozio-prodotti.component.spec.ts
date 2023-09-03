import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NegozioProdottiComponent } from './negozio-prodotti.component';

describe('NegozioProdottiComponent', () => {
  let component: NegozioProdottiComponent;
  let fixture: ComponentFixture<NegozioProdottiComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NegozioProdottiComponent]
    });
    fixture = TestBed.createComponent(NegozioProdottiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
