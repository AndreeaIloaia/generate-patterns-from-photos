import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthtestComponent } from './authtest/authtest.component';
import { DrawComponent } from './draw/draw.component';
import { EditComponent } from './edit/edit.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { PatternComponent } from './pattern/pattern.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuardService } from './services/auth/auth_guard.service';


const routes: Routes = [
  {
    path: 'login',
    component: AuthtestComponent
  },
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'register',
    component: AuthtestComponent
  }, 
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuardService]
  }, 
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuardService]
  }, 
  {
    path: 'edit',
    component: EditComponent,
    canActivate: [AuthGuardService]
  }, 
  {
    path: 'draw',
    component: DrawComponent,
    canActivate: [AuthGuardService]
  },
  {
    path: 'patterns',
    component: PatternComponent,
    canActivate: [AuthGuardService]
  }, 
  {
    path: '**',
    redirectTo: ''
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
