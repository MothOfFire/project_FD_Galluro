import VueRouter from 'vue-router';
import login from '../components/login/Login';
import register from '../components/login/Register';
import personalCenter from '../components/employee/personalCenter';
import index from '../components/Index';
import employeeMain from '../components/employee/employeeMain';
import employeeStaffMain from '../components/employee/employeeStaffMain';
import equipmentMain from "@/components/equipment/equipmentMain";
import repositoryMain from "@/components/equipment/repositoryMain";
import otherTreesMain from "@/components/teaPantation/otherTreesMain";
import teaPantationParkMain from "@/components/teaPantation/teaPantationParkMain";;
import teaTreesMain from "@/components/teaPantation/teaTreesMain";
import parkadeMain from "@/components/visitor/parkadeMain";
import visitorRegisterMain from "@/components/visitor/visitorRegisterMain";
import visitorMain from "@/components/visitor/visitorMain";
import personnelVisualizationMain from "@/components/visualization/personnelVisualizationMain";
import resourcesVisualizationMain from "@/components/visualization/resourcesVisualizationMain";
import visitorOutMain from "@/components/visitor/visitorOutMain";
import teaProductionMain from "@/components/visualization/teaProductionMain";
import tasteTeaMain from "@/components/teaLeaf/tasteTeaMain";
import teaProcessMain from "@/components/teaLeaf/teaProcessMain";
import teaPluckMain from "@/components/teaLeaf/teaPluckMain";
import repositoryManageMain from "@/components/equipment/repositoryManageMain";
import approvalMain from "@/components/equipment/approvalMain";
import submitMain from "@/components/equipment/submitMain";
import addTea from "@/components/teaLeaf/addTea";

const routes = [
    {
        path:'/',
        name:'login',
        component:login
    },
    {
        path:'/index',
        name:'index',
        component:index,
        children:[
            {
                path: '/personalCenter',
                name: 'personalCenter',
                meta:{
                    title:"个人中心"
                },
                component: personalCenter
            },
            {
                path:'/employeeMain',
                name:'employeeMain',
                meta:{
                    title:"人员信息"
                },
                component:employeeMain
            },
            {
                path:'/employeeStaffMain',
                name:'employeeStaffMain',
                meta:{
                    title:"人员信息"
                },
                component:employeeStaffMain
            },
            {
                path:'/equipmentMain',
                name:'equipmentMain',
                meta:{
                    title:"设备信息"
                },
                component:equipmentMain
            },
            {
                path:'/repositoryMain',
                name:'repositoryMain',
                meta:{
                    title:"后勤仓库信息"
                },
                component:repositoryMain
            },
            {
                path:'/repositoryManageMain',
                name:'repositoryManageMain',
                meta:{
                    title:"后勤仓库管理"
                },
                component:repositoryManageMain
            },
            {
                path:'/approvalMain',
                name:'approvalMain',
                meta:{
                    title:"审批"
                },
                component:approvalMain
            },
            {
                path:'/submitMain',
                name:'submitMain',
                meta:{
                    title:"提交"
                },
                component:submitMain
            },
            {
                path:'/otherTreesMain',
                name:'otherTreesMain',
                meta:{
                    title:"景观树信息"
                },
                component:otherTreesMain
            },
            {
                path:'/teaPantationParkMain',
                name:'teaPantationParkMain',
                meta:{
                    title:"园区信息"
                },
                component:teaPantationParkMain
            },
            {
                path:'/teaTreesMain',
                name:'teaTreesMain',
                meta:{
                    title:"茶树信息"
                },
                component:teaTreesMain
            },
            {
                path:'/parkadeMain',
                name:'parkadeMain',
                meta:{
                    title:"停车场信息"
                },
                component:parkadeMain
            },
            {
                path:'/visitorMain',
                name:'visitorMain',
                meta:{
                    title:"游客信息"
                },
                component:visitorMain
            },
            {
                path:'/visitorRegisterMain',
                name:'visitorRegisterMain',
                meta:{
                    title:"游客进园登记"
                },
                component:visitorRegisterMain
            },
            {
                path:'/visitorOutMain',
                name:'visitorOutMain',
                meta:{
                    title:"游客出园登记"
                },
                component:visitorOutMain
            },
            {
                path:'/tasteTeaMain',
                name:'tasteTeaMain',
                meta:{
                    title:"茶叶产品"
                },
                component:tasteTeaMain
            },
            {
                path:'/addTea',
                name:'addTea',
                meta:{
                    title:"添加茶叶产品"
                },
                component:addTea
            },
            {
                path:'/teaProcessMain',
                name:'teaProcessMain',
                meta:{
                    title:"茶叶加工"
                },
                component:teaProcessMain
            },
            {
                path:'/teaPluckMain',
                name:'teaPluckMain',
                meta:{
                    title:"茶叶采摘"
                },
                component:teaPluckMain
            },
            {
                path:'/personnelVisualizationMain',
                name:'personnelVisualizationMain',
                meta:{
                    title:"人员信息可视化"
                },
                component:personnelVisualizationMain
            },
            {
                path: '/teaProductionMain',
                name: 'teaProductionMain',
                meta: {
                    title: "茶园生产信息统计"
                },
                component: teaProductionMain
            },
            {
                path:'/resourcesVisualizationMain',
                name:'resourcesVisualizationMain',
                meta:{
                    title:"设备资源信息"
                },
                component:resourcesVisualizationMain
            },
        ]
    },
    {
        path:'/register',
        name:'register',
        component:register
    },
];

const router = new VueRouter({
    mode:'history',
    routes
});


export default router;