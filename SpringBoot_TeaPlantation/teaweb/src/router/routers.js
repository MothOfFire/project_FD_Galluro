import VueRouter from 'vue-router';
import login from '../components/Login';
import index from '../components/Index';
import register from '../components/Register';

const routes = [
    {
        path:'',
        name:'login',
        component:login
    },
    {
        path:'/index',
        name:'index',
        component:index
    },
    {
        path:'/register',
        name:'register',
        component:register
    }
];

const router = new VueRouter({
    mode:'history',
    routes
});

export default router;