import { Injectable, signal } from '@angular/core';
import { ServiceCategoryResponse, ServiceCategoryService } from '../../../core/api';
import { finalize } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ServiceCategoriesFacade {
  private readonly _services = signal<ServiceCategoryResponse[]>([]);
  private readonly _loading = signal(false);
  private readonly _error = signal<string | null>(null);
  private loaded = false;

  public readonly services = this._services.asReadonly();
  public readonly loading = this._loading.asReadonly();
  public readonly error = this._error.asReadonly();

  constructor(private serviceCategoryService: ServiceCategoryService) {}

  public loadServices(): void {
    if (this.loaded) {
      return;
    }

    this._loading.set(true);
    this._error.set(null);

    this.serviceCategoryService.getAllServices().pipe(
      finalize(() => this._loading.set(false))
    ).subscribe({
      next: (res) => {
        this._services.set(res);
        this.loaded = true;
      },
      error: (err) => this._error.set(this.toMessage(err))
    });
  }

  private toMessage(err: unknown): string {
    return err instanceof Error ? err.message : 'Failed to load services';
  }
}
