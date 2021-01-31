Vue.component("location-form-map", {
    template: `
    <div>
        <div ref="map-root" style="width: 100%; height: 300px;">
        </div>
    </div>
    `,
    data: function () {
        return {
            map: null,
            markerFeature: null,
        };
    },

    props: ["coordinates", "zoom", "moovable", "updateSizeTrigger"],
    watch: {
        immediate: true,
        coordinates: function (newCoordinates, oldCoordinates) {
            this.moveMapView(newCoordinates);
            this.markerFeature
                .getGeometry()
                .setCoordinates(
                    ol.proj.transform(newCoordinates, "EPSG:4326", "EPSG:3857")
                );
        },
		updateSizeTrigger: function(){
			setTimeout(() => this.map.updateSize(), 300);
		}
    },

    methods: {
        moveMarkerOnClick: function (event) {
            this.markerFeature.getGeometry().setCoordinates(event.coordinate);

            const transformedCoordinates = ol.proj.transform(
                event.coordinate,
                "EPSG:3857",
                "EPSG:4326"
            );

            this.updateCoordinates(transformedCoordinates);
        },
        initMap: function () {
            this.markerFeature = new ol.Feature({
                geometry: new ol.geom.Point(ol.proj.fromLonLat(this.coordinates)),
            });

			this.markerFeature.setStyle(new ol.style.Style({
		    image: new ol.style.Icon({
			  scale: 0.15,
		      src: '../WebProjekat/rest/images/marker.png',
		    })}));

            vectorLayer = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [this.markerFeature],
					wrapX: true,
                }),
				wrapX: false,
            });

            this.map = new ol.Map({
                target: this.$refs["map-root"],
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM({
							wrapX: false,
						}),
                    }),
                    vectorLayer,
                ],

                view: new ol.View({
                    zoom: this.zoom,
                    center: ol.proj.transform(
	                    this.coordinates,
	                    "EPSG:4326",
	                    "EPSG:3857"
               		),
                    constrainResolution: true,
                }),
            });
			if(this.moovable){
				this.map.on("singleclick", this.moveMarkerOnClick);
			}
        },
        updateCoordinates: function (coordinates) {
            this.$emit("update-coordinates", coordinates);
        },
        moveMapView: function (newCoordinates) {
            this.map.getView().animate({
                center: ol.proj.transform(
                    newCoordinates,
                    "EPSG:4326",
                    "EPSG:3857"
                ),
                duration: 500,
            });
        },
    },

    mounted() {
        this.initMap();
    },
});
