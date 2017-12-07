var CardOnList = Vue.extend({
  template: '#list-row-template',
  props: ['m']
});

Vue.component('modal', {
	  template: '#modal-template',
	  props: ['prevcond', 'nextcond']
	});

var app = new Vue({
  el: '#app',
  data: {
	total: 0,
    downloadedList: [],
    errors: [],
    detailIndex : -1,
    detailModel : null,
    showModal: false,
    isFav: false
  },
  
  mounted: function () {
	  	this.loadMore();
	  },
methods: {
	loadMore: function () {
		axios.get('rest/cards/ASC/', {
		    params: {
		        from: this.downloadedList.length,
		        max: 200,
		        //rarity:'Legendary',
		        //mechanicsFilter:'Deathrattle'
		      }
		    })
	    .then(response => {
	    	console.log("Loading:", response.data.list.length, 
	    			"Loaded:",this.downloadedList.length, 
	    			"Total:", this.total);
	      	this.total = response.data.total;
	      	this.addToList(this.downloadedList.length, response.data.list);
	      	this.downloadedList = this.downloadedList.concat(response.data.list);
	    	
	    	if(this.total > this.downloadedList.length) {
	    		this.loadMore();
	    	}
	    })
	    .catch(e => {
	      this.errors.push(e)
	    })
	},
	
	addToList: function (startIndex, newData) {
		var first = true;
		var component = null;
		
		for (i in newData) {
			if (i % 5 == 0) {
				if(first){
					first = false;
				} else {
					this.appendToClusterize(component);
				}
				var component = new CardOnList();
				component.m=[];
			}
			var cardData = newData[i];
			var listItem = {};
			listItem.src=cardData.img;
			listItem.clk="app.lstClk("+(Number(startIndex)+Number(i))+",'"+cardData.cardId+"')";
			component.m.push(listItem);
		}
		this.appendToClusterize(component);//add the rest of items
	},
	
	appendToClusterize: function (component) {
		component= component.$mount();
		clusterize.append([component.$el.outerHTML]);
	},
	
    lstClk: function (idx, cardId) {
    	console.log("clicked", idx, cardId);
    	this.detailIndex= idx;
    	this.isFav = (localStorage.getItem(cardId) == 1);
    	
    	axios.get('rest/card/'+cardId)
	    .then(response => {
	    	this.detailModel = response.data;
	    	this.showModal = true;
	    })
	    .catch(e => {
	      this.errors.push(e)
	    })
	},
	  
	stepDetail: function (offset) {
		this.detailIndex+=offset;
		this.lstClk(
				this.detailIndex,
				this.downloadedList[this.detailIndex].cardId
		);
	},
	
  imgError: function (img) {
	  img.onerror=null;
	  img.src='//img.ogol.io/splendo.com/9y48478.png?w=100&h=185';
  },
	
	fav: function () {
		localStorage.setItem(this.detailModel.cardId, this.isFav ? 0 : 1);
		this.isFav = !this.isFav;
	}
		
}

})

var clData = [];
var clusterize = new Clusterize({
	  rows: clData,
	  scrollId: 'scrollArea',
	  contentId: 'contentArea',
	  tag : 'tr',
	  rows_in_block:3,
	  keep_parity:false
	});