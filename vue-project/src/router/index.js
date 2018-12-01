import Vue from 'vue'
import Router from 'vue-router'
import SaleCommodity from '../components/page/SaleCommodity'
import Stock from '../components/page/Stock'
import Commodity from '../components/page/Commodity'
import PurchaseCommodity from '../components/page/PurchaseCommodity'
import Shangjia from '../components/page/Shangjia'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:'/',
      redirect: '/saleCommodity'
    },
    {
      path: '/saleCommodity',
      name: '售货',
      component: SaleCommodity
    },
    {
      path: '/stock',
      // name: '上架',
      component: Stock,
      redirect: '/stock/commodity',
      children: [
        {
          path: 'commodity',
          name: '商品类别',
          component: Commodity
        },
        {
          path: 'purchaseCommodity',
          name: '进货',
          component: PurchaseCommodity
        },
        {
          path: 'shangjia',
          name: '上架',
          component: Shangjia
        }
      ]
    }
  ]
})
