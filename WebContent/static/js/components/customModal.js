Vue.component('custom-modal',
{
    template:
    `
	<div class="modal fade" :id="modalName" tabindex="-1" role="dialog" v-bind:aria-hidden=true>
		<div class="modal-dialog modal-lg" role="document">
		    <div class="modal-content">
		    	<div class="modal-header">
		    		<h5 class="modal-title" id="commentModalLabel">{{title}}</h5>
		    		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
		    			<span aria-hidden="true">&times;</span>
		    		</button>
		    	</div>
				<div class="modal-body">
				  	<slot></slot>
				</div>
			</div>
		</div>
	</div>
    `,

	props: ["modalName", "title"],

    data: function() {
 		return {
	
		};
    },
    
    methods: {
    
    },
		
	mounted: function() {

	}
});