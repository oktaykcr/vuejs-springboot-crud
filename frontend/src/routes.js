import Home from './components/Home'
import UserEdit from './components/User/UserEdit'
import UserCreate from './components/User/UserCreate'


export const routes = [
    { path: '/', component: Home},
    { path: '/create', component: UserCreate},
    { path: '/:id/edit', component: UserEdit}
];