Vue.component('confirmation-modal',
{
    template:
    `
	<custom-modal v-on:closed="$emit('closed')" :modalName="modalName" :title="title">
		<div>
			
			<p>
				<slot>
				</slot>
			</p>
			
			<div class="modal-footer">
				<button type="button" :class="'btn btn-' + type" v-on:click="callback(callbackData)">Yes</button>
				<button type="button" class="btn btn-secondary" v-on:click="hideModal" data-target="#addLocationModal">Cancel</button>
			</div>
		</div>
	</custom-modal>
    `,

	props: ["modalName", "title", "callback", "callbackData", "type"],

    data: function() {
 		return {
	
		};
    },
    
    methods: {
        hideModal: function(){
			$(`#${this.modalName}`).modal('toggle');
			this.$emit("closed");
		}
    },
		
	mounted: function() {

	}
});