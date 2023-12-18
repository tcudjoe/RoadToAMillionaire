import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";

export const authGuard: CanActivateFn = () => {
  const router = inject(Router);

  if (!localStorage.getItem('token')){
    router.navigate(["/auth/login"])
    return false;
  }

  return true;
};
