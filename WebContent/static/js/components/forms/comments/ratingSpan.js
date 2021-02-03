Vue.component('rating-span', {
	template:
		`
		<div>
			<span style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<2" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=2" style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<3" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=3" style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<4" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=4" style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<5" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=5" style="font-size:130%;color:gold;">&starf;</span>
		</div>
	`,
	
	props:["rating"],

	data: function() {
		return {

		}
	},

	methods:
	{
	}
});