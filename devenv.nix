{ pkgs, ... }: {
  # https://devenv.sh/reference/options/
  packages = [ pkgs.figlet pkgs.lolcat pkgs.babashka pkgs.cljfmt];
  languages.clojure.enable = true;

  enterShell = ''
    figlet -f small -k "Kahawa Devenv" | lolcat -F 0.5 -ad 1 -s 30
  '';

  # Run an arbitrary program on start
  #processes.run.exec = "./test.bb";

}
