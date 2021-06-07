import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

import {LoginService} from './services/login/login.service';
import { RegisterComponent } from './register/register.component';
import { RegisterService } from './services/register/register.service';
import { HomeComponent } from './home/home.component';
import { DrawComponent } from './draw/draw.component';
import { TokenInterceptor } from './services/token.interceptor';
import { AuthtestComponent } from './authtest/authtest.component';
import { NavComponent } from './nav/nav.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProgressComponent } from './draw/progress/progress.component';
import { PatternComponent } from './pattern/pattern.component';
import { ProfileComponent } from './profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    DrawComponent,
    AuthtestComponent,
    NavComponent,
    ProgressComponent,
    PatternComponent,
    ProfileComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
  ],
  providers: [LoginService, RegisterService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
