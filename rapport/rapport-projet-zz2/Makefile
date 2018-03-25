SRC = main
#REP= ./sources_gnuplot

DEBUG_MODE ?= N
LATEX = pdflatex --shell-escape
EDITOR = subl

ifeq ($(DEBUG_MODE), Y)
#LATEX += -interaction=nonstopmode
else
LATEX += -interaction=batchmode
endif

BIBTEX = biber -u -U -q

ECHO = /bin/echo -e

.PHONY:all $(REP)

#all: $(REP) ${SRC}.pdf
all: complet

$(REP):
	@$(MAKE) -C $@ $(MAKECMDGOALS)

clean: $(REP)
	@$(ECHO) "suppression des fichiers de compilation"
	@# fichiers de compilation latex
	@rm -f *.log *.aux *.dvi *.toc *.lot *.lof *.loa *.lol *.out *.idx
	@# fichiers de bibtex
	@rm -f *.bbl *.blg

T_START=$(shell date -d now +%s)

complet:
	@$(ECHO) "\033[31mCompilation complète\033[0m"
	@$(ECHO) "\033[1m $$(date +%H:%M:%S) \033[0m"
	@notify-send --expire-time=2700 -i /usr/share/icons/gnome/32x32/emblems/emblem-new.png "Début de compilation" "À : $$(date +%H:%M:%S)"
	@$(ECHO) "\033[32m\t Compilation n°1\033[0m"
	@${LATEX} ${SRC}
	@$(ECHO) "\033[1m $$(date +%H:%M:%S) \033[0m"
	@notify-send -i /usr/share/icons/gnome/32x32/emblems/emblem-default.png "Compilation n°1" "Check : $$(date +%H:%M:%S)"
	@$(ECHO) "\033[36m\t Bibliographie\033[0m"; ${BIBTEX} ${SRC} ; notify-send -i /usr/share/icons/gnome/32x32/emblems/emblem-documents.png "Bibliographie" "Check : $$(date +%H:%M:%S)"; fi
	@$(ECHO) "\033[32m\t Compilation n°2\033[0m"
	@${LATEX} ${SRC}
	@$(ECHO) "\033[1m $$(date +%H:%M:%S) \033[0m"
	@notify-send -i /usr/share/icons/gnome/32x32/emblems/emblem-default.png "Compilation n°2" "Check : $$(date +%H:%M:%S)"
	@$(ECHO) "\033[32m\t Compilation n°3\033[0m"
	@${LATEX} ${SRC}
	@$(ECHO) "\033[1m $$(date +%H:%M:%S) \033[0m"
	@notify-send -i /usr/share/icons/gnome/32x32/emblems/emblem-generic.png "Compilation n°3" "Check : $$(date +%H:%M:%S)"
	@$(ECHO) "\033[33mTemps de compilation : $$(( $$(date -d now +%s) - $(T_START) )) s\033[0m"
	@notify-send -i /usr/share/icons/gnome/32x32/actions/document-open-recent.png "Temps de compilation" "$$(( $$(date -d now +%s) - $(T_START) )) s"

initial: propre
	@$(ECHO) „suppression des fichiers cibles“
	@rm -f ${SRC}.ps ${SRC}.pdf

bib:
	${BIBTEX} ${SRC}

one :
	@$(ECHO) "\033[31mCompilation juste une fois\033[0m"
	@$(ECHO) "\033[1m $$(date +%H:%M:%S) \033[0m"
	@notify-send --expire-time=27000 -i /usr/share/icons/gnome/32x32/emblems/emblem-new.png "Début de compilation" "À : $$(date +%H:%M:%S)"
	@${LATEX} ${SRC}
	@$(ECHO) "\033[1m $$(date +%H:%M:%S) \033[0m"
	@notify-send -i /usr/share/icons/gnome/32x32/emblems/emblem-generic.png "Compilation" "Check : $(date +%H:%M:%S)"
	@$(ECHO) "\033[33mTemps de compilation : $$(( $$(date -d now +%s) - $(T_START) )) s\033[0m"
	@notify-send -i /usr/share/icons/gnome/32x32/actions/document-open-recent.png "Temps de compilation" "$$(( $$(date -d now +%s) - $(T_START) )) s"
	
open:
	@$(EDITOR) $(SRC).tex part/*.tex &

