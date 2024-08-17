{ pkgs, ... }: {
  # https://devenv.sh/reference/options/
  packages = [ pkgs.figlet pkgs.lolcat pkgs.babashka pkgs.cljfmt];
  languages.clojure.enable = true;
  languages.java.enable = true;
  languages.java.jdk.package = pkgs.jdk22;

  enterShell = ''
    figlet -f small -k "Kahawa Devenv" | lolcat -F 0.5 -ad 1 -s 30
    export PATH=$PWD/bin:$PATH
  '';
  processes = {
    launchpad.exec = "bin/launchpad";
    kaocha.exec = "bin/kaocha --watch";
  };

  # Run an arbitrary program on start
  #processes.run.exec = "./test.bb";

}
