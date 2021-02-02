Vue.component("navbar", {
    template: `
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	  <router-link class="navbar-brand" to="/">WebProjekat</router-link>
	  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    <span class="navbar-toggler-icon"></span>
	  </button>
	
	  <div class="collapse navbar-collapse" id="navbarSupportedContent">
	    <ul class="navbar-nav mr-auto">
	      <li class="nav-item active">
	        <router-link class="nav-link" to="/">Home <span class="sr-only">(current)</span></router-link>
	      </li>
	      <li v-for="option in activeOptions" :key="option.name" class="nav-item active">
	        <router-link :to="option.path" class="nav-link">{{option.name}}</router-link>
	      </li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li v-if="this.sharedState.userLoggedIn" class="nav-item active">
				<div class="dropdown">
				  <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    {{loggedUsername}}
				  </button>
				  <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenuButton">
				    <router-link v-for="userOption in userOptions" :key="userOption.name"
					:to="userOption.path" 
					class="dropdown-item">
						{{userOption.name}}
					</router-link>
				    <div class="dropdown-divider"></div>
					<a v-on:click="logout" href="#" class="dropdown-item">Logout</a>
				  </div>
				</div>
	      	</li>
			<li v-else class="nav-item active">
	        	<router-link to="/login" href="#" class="nav-link">Login</router-link>
	      	</li>
		</ul>
	  </div>
	</nav>
    `,

	data: function(){
		return {
			sharedState: store.state,
			loggedUsername: null,
			activeOptions: [],
			defaultOptions: [],
			buyerOptions: [
				{name: "Tickets", path: "/buyer-tickets"},
				{name : "Cart", path : "/cart"},
				{name: "My Comments", path: "/my-comments"},

			],
			sellerOptions: [				
				{name : "Locations", path : "/locations"},

			],
			adminOptions: [
				{name : "Locations", path : "/locations"},
				{name : "Users", path : "/users"},
				{name : "Distrustful Buyers", path : "/distrustful-buyers"},

			],
			userOptions:[
				{name: "Account", path:"account"},
			],
		}
	},
	
	mounted: function(){
		this.sharedState.userLoggedIn = !!window.localStorage.getObject("loggedUser");
		this.changeOptions();
	},
	
	
	watch: {
		"sharedState.userLoggedIn" : function(){
			this.changeOptions();
		}
	},
	
	methods: {
		changeOptions: function(){
			if(this.sharedState.userLoggedIn){
				
				const {role, username} = window.localStorage.getObject("loggedUser").user;
				this.loggedUsername = username;
				
				switch(role) {
				case "Buyer":
					this.activeOptions = this.buyerOptions;
					break;
				case "Seller":
					this.activeOptions = this.sellerOptions;
					break;
				case "Administrator":
					this.activeOptions = this.adminOptions;
				    break;
				  default:
				    this.activeOptions = this.defaultOptions;
				}
			}else{
				this.activeOptions = this.defaultOptions;
			}
		},
		getBuyerId : function(){
			return this.sharedState.userLoggedIn ? localStorage.getObject("loggedUser").user.id : "";
		},
		
		logout: function(event){
			event.preventDefault();
			window.localStorage.removeItem("loggedUser");
			window.localStorage.removeItem("shoppingCart");
			this.sharedState.userLoggedIn = false;
			this.sharedState.loggedUsername = null;
			this.$router.replace("/")
		}
	}
});
