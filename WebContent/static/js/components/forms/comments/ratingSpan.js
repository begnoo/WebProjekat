Vue.component('rating-span', {
	template:
		`
		<div>
			<span v-on:click="changeRating(1)" style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<2" v-on:click="changeRating(2)" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=2" v-on:click="changeRating(2)" style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<3" v-on:click="changeRating(3)" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=3" v-on:click="changeRating(3)" style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<4" v-on:click="changeRating(4)" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=4" v-on:click="changeRating(4)" style="font-size:130%;color:gold;">&starf;</span>
			<span v-if="rating<5" v-on:click="changeRating(5)" style="font-size:130%;color:gold;">&star;</span>
			<span v-if="rating>=5" v-on:click="changeRating(5)" style="font-size:130%;color:gold;">&starf;</span>
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