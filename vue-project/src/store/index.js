import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import actions from './actions'
import commodity from './modules/commodity'
// import saleStrategies from './modules/saleStrategies'
// import saleCategories from './modules/saleCategories'
// import saleHistory from './modules/saleHistory'
// import rechargeHistory from './modules/rechargeHistory'
// import customerFeeDetails from './modules/customerFeeDetails'

Vue.use(Vuex);

export default new Vuex.Store({
  getters,
  actions,
  modules: {
    commodity,
    // saleStrategies,
    // saleCategories,
    // saleHistory,
    // rechargeHistory,
    // customerFeeDetails
  }
})
