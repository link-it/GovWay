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
    platform: karate.os.type,
    config_loader_path: "path_to_config_loader/distrib",
    modipa_test_bundle: karate.toAbsolutePath("classpath:configurazioni-govway/modipaTestBundle.zip")
  }
}
