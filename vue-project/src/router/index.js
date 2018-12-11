import Vue from 'vue'
import Router from 'vue-router'
import CartCommodity from '../components/page/CartCommodity'
import Stock from '../components/page/Stock'
import Commodity from '../components/page/Commodity'
import PurchaseCommodity from '../components/page/PurchaseCommodity'
import Unpack from '../components/page/Unpack'

Vue.use(Router)

export default new Router({
  mode:'history',
  base:'siqi',
  routes: [
    {
      path:'/',
      redirect: '/cartCommodity'
    },
    {
      path: '/cartCommodity',
      name: '售货',
      component: CartCommodity
    },
    {
      path: '/stock',
      // name: '上架',
      component: Stock,
      redirect: '/stock/commodity',
      children: [
        {
          path: 'commodity',
          name: '商品',
          component: Commodity
        },
        {
          path: 'purchaseCommodity',
          name: '进货',
          component: PurchaseCommodity
        },
        {
          path: 'unpack',
          name: '拆箱',
          component: Unpack
        }
      ]
    }
  ]
})
