Vue.component('pagination',
{
    template:
    `
	      <nav aria-label="Page navigation example">
	        <ul class="pagination justify-content-center">
	            <li
		            class="page-item"
		            v-bind:class="{disabled : selectedPage === 1}"
		            v-on:click="changePageFor(-1)"
	            >
		            <p class="page-link pagination-item" aria-label="Previous">
		                <span aria-hidden="true">&laquo;</span>
		            </p>
	            </li>
	           
           		<li
	           		v-for="page in pages"
	           		class="page-item"
	           		v-bind:class="{active: page === selectedPage}"
	           		v-on:click="setPage(page)"
           		>
	            	<p class="page-link pagination-item">{{ page }}</p>
	           	</li>
	           
	            <li
		            class="page-item"
		            v-bind:class="{disabled: this.pageData.length < this.pageSize}"
		            v-on:click="changePageFor(1)"
	            >
		            <p class="page-link pagination-item" aria-label="Next">
		                <span aria-hidden="true">&raquo;</span>
		            </p>
		            </li>
		        </ul>
	  		</nav>
    `,

	props: ["restConfig", "pageSize"],

    data: function() {
        return {
            pageData: [],
            nextPageData: [],      
            selectedPage: 1,
            pages: [1, 2, 3],
            
        }
    },
    
    methods: {
		resetPages: function(){
			this.selectedPage = 1;
			this.pages = [1, 2, 3];	
			this.loadPage(this.selectedPage, () =>
	    	{
	    		this.pageData = this.nextPageData;
				this.emitData();
	
	    	});
		},
		
    	loadPage : function(page, callback) {
			this.restConfig.url = `${this.restConfig.pageTemp}?number=${page}&size=${this.pageSize}`;
    		axios(this.restConfig, this.params)
             	 .then(response =>
             	 {
         	 		this.nextPageData = response.data;
         	 		callback();
         	 	});
    	},
    
		
		setPage: function(pageNumber) {
			let changeFor = pageNumber - this.selectedPage;
			this.changePageFor(changeFor);
		},
		
		changePageFor: function(changeFor) {
			let nextPage = this.selectedPage;
			
			if(changeFor < 0 && this.selectedPage + changeFor >= 1) {
				nextPage += changeFor;
			} else if (changeFor > 0 && this.pageData.length === this.pageSize) { 
				nextPage += changeFor;
			}
			
			this.loadPage(nextPage, () =>
			{
				if(this.nextPageData.length > 0) {
					this.pageData = this.nextPageData;
					this.selectedPage = nextPage;
					this.changePagesList();
					this.emitData();
				}
			});
		},
		
    	changePagesList: function() {
			if(this.selectedPage >= 2) {
				this.pages = [this.selectedPage - 1, this.selectedPage, this.selectedPage + 1];
			}
		},
		emitData: function(){
			this.$emit("update-page-data", {emittedData: this.pageData, selectedPage: this.selectedPage});

		}
    },

    mounted: function() {
    	this.loadPage(this.selectedPage, () =>
    	{
    		this.pageData = this.nextPageData;
			this.emitData();

    	});
    },

});