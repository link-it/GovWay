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
  
  return { 
    govway_base_path: "http://localhost:8080/govway",
    platform: "unix", // || windows
    config_loader_path: "/home/froggo/sorgenti/link_it/GOVWAY/GovWay/tools/command_line_interfaces/config_loader/distrib",

    modipa_demo_test_zip: "/home/froggo/sorgenti/link_it/GOVWAY/GovWay/protocolli/modipa/testsuite/src/configurazioni-govway/modipaDemoTest.zip",
    modipa_demo_test_http_zip: "/home/froggo/sorgenti/link_it/GOVWAY/GovWay/protocolli/modipa/testsuite/src/configurazioni-govway/modipaDemoTestHttp.zip"
  }
}
