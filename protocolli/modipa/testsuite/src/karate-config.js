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
    govway_base_path: karate.properties["govway_base_path"]
  }
}
