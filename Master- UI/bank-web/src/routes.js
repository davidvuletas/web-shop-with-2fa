import LandingPage from './components/landing-page/Landing.vue'
import Login from './components/landing-page/Login.vue'
import Register from './components/landing-page/Register.vue'
import QRCode from './components/qr-code/QRCode.vue'

export const routes = [
    {path: '', name: 'landing-page', component: LandingPage},
    {path: '/login', name: 'login', component: Login},
    {path: '/register', name: 'register', component: Register},
    {path: '/qr-code/:name', name: 'qr-code', component: QRCode},
    {path: '*', redirect: '/'} 
];