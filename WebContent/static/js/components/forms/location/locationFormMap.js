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

    props: ["coordinates"],
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
                geometry: new ol.geom.Point(ol.proj.fromLonLat([-2, 53])),
            });

            vectorLayer = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [this.markerFeature],
                }),
            });

            this.map = new ol.Map({
                target: this.$refs["map-root"],
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM(),
                    }),
                    vectorLayer,
                ],

                view: new ol.View({
                    zoom: 0,
                    center: [0, 0],
                    constrainResolution: true,
                }),
            });

            this.map.on("singleclick", this.moveMarkerOnClick);
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
