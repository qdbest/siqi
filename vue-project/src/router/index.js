import Vue from 'vue'
import Router from 'vue-router'
import CartCommodity from '../components/page/CartCommodity'
import Stock from '../components/page/Stock'
import Commodity from '../components/page/Commodity'
import PurchaseCommodity from '../components/page/PurchaseCommodity'

Vue.use(Router)

export default new Router({
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
          name: '商品类别',
          component: Commodity
        },
        {
          path: 'purchaseCommodity',
          name: '进货',
          component: PurchaseCommodity
        }
      ]
    }
  ]
})
