import {getRequest, postRequest} from "../../../api";

'../../../api'
const state = {
  // 商品列表
  commodities: [],
  // 商品总数
  total: 0,
};

const getters = {};

const actions = {
  listCommodities({state, commit}, params) {
    getRequest('api/commodity', params)
      .then(response => {
        commit('commoditiesChange', response.data.data.content);
        commit('totalChange', response.data.data.totalElements);
      });
  }
};

const mutations = {
  commoditiesChange(state, commodities) {
    state.commodities = commodities;
  },
  totalChange(state, total) {
    state.total = total;
  },
  incrementTotal(state) {
    state.total++;
  }
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
}
