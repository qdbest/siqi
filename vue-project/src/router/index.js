import Vue from 'vue'
import Router from 'vue-router'
import Sell from '../components/page/Sell'
import Stock from '../components/page/Stock'
import Commodity from '../components/page/Commodity'
import StockCommodity from '../components/page/StockCommodity'
import Shangjia from '../components/page/Shangjia'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path:'/',
      redirect: '/sell'
    },
    {
      path: '/sell',
      name: '售货',
      component: Sell
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
          path: 'stockCommodity',
          name: '进货',
          component: StockCommodity
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
