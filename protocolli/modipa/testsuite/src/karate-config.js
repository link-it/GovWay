function fn() {    
  var env = karate.env; // get system property 'karate.env'
  karate.log('karate.env system property was:', env);
  if (!env) {
    env = 'dev';
  }
  if (env == 'dev') {
    // customize
    // e.g. config.foo = 'bar';
  } else if (env == 'e2e') {
    // customize
  }
  karate.configure('connectTimeout',  1000000);
  karate.configure('readTimeout', 1000000);
  var protocol = 'http';
  var govwayUrl = 'http://localhost:8080';
  
  return { 
    config_loader_path: "/home/froggo/sorgenti/link_it/GOVWAY/GovWay/tools/command_line_interfaces/config_loader/distrib",
    modipa_demo_test_zip: "/home/froggo/sorgenti/link_it/GOVWAY/modipaDemoTest.zip",
    modipa_demo_test_http_zip: "/home/froggo/sorgenti/link_it/GOVWAY/modipaDemoTestHttp.zip",
    
    url_invocazione_fruizione: "http://localhost:8080/govway/rest/out/DemoSoggettoFruitore/DemoSoggettoErogatore/ApiDemoBlockingRest/v1"
  }
}
