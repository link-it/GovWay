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

  connect_timeout = karate.properties["connect_timeout"] || 1000000
  read_timeout = karate.properties["read_timeout"] || 1000000

  karate.configure('connectTimeout',  connect_timeout);
  karate.configure('readTimeout', read_timeout);
  
  return { 
    govway_base_path: karate.properties["govway_base_path"]
  }
}
