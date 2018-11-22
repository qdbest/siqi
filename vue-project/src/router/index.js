import Vue from 'vue'
import Router from 'vue-router'
import Sell from '../components/page/Sell'
import Stock from '../components/page/Stock'
import Category from '../components/page/Category'
import Jinhuo from '../components/page/Jinhuo'
import Shangjia from '../components/page/Shangjia'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/sell',
      name: '售货',
      component: Sell
    },
    {
      path: '/stock',
      name: '上架',
      component: Stock,
      redirect: '/stock/category',
      children: [
        {
          path: 'category',
          name: '商品类别',
          component: Category
        },
        {
          path: 'jinhuo',
          name: '进货',
          component: Jinhuo
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
