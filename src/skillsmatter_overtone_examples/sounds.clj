(ns skillsmatter-overtone-examples.sounds
  (:use overtone.live))

;; First, some basic oscillators

(definst basic-sine [freq 440]
  (sin-osc freq))

(comment
  (basic-sine)     ; tuning A
  (stop)
  (basic-sine 440) ; same thing
  (basic-sine 880) ; doubling freq goes up one octave
  (basic-sine 220) ; halving freq goes down one octave
  )

(definst basic-saw [freq 440]
  (saw freq))

(comment
  (basic-saw)
  (stop)
  (basic-saw 880)
  (basic-saw 220)
  )

(definst basic-square [freq 440]
  (square freq))

(comment
  (basic-square)
  (stop)
  (basic-square 880)
  (basic-square 220)
  )

;; Combining oscillators by mixing signals

;; whole number multiples of a frequence blend well and change the sound
(definst multiple-sines [freq 440]
  (+
   (sin-osc freq)
   (sin-osc (* 2 freq))
   (sin-osc (* 3 freq))))

(comment
  (multiple-sines 440)
  ;;compare with:
  (basic-sine 440)
  )

;; slightly different, detuned, frequencies clash and give a harsher
;; sound
;; this example also demonstrates passing a vector of values to an
;; oscillator in order to create multiple instances and mix them together
(definst detuned-saws [freq 440]
  (mix (saw (* freq [0.99 1 1.01]))))

(comment
  (detuned-saws)
  ;; compare:
  (basic-saw)
  (detuned-saws 100) ;; compare with below
  )

;; we can use one oscillator as the input to another. Here we control
;; the frequency of a sine wave with another sine wave to produce a
;; vibrato effect:
(definst wobbled-sin [pitch-freq 440 wobble-freq 5 wobble-depth 5]
  (let [wobbler (* wobble-depth (sin-osc wobble-freq))
        freq (+ pitch-freq wobbler)]
    (sin-osc freq)))

(comment
  (wobbled-sin)
  ;; you can try it with deeper, slower wobbles:
  (wobbled-sin 440 2 50)
  ;; if you make the wobble much faster, strange sounds emerge:
  (wobbled-sin 440 100 50)
  )

;; Combining the previous two ideas, we have the start of a dubstep
;; oscillator:

(definst dubstep [freq 100 wobble-freq 2]
  (let [sweep (lin-exp (lf-saw wobble-freq) -1 1 40 5000)
        son   (mix (saw (* freq [0.99 1 1.01])))]
    (lpf son sweep)))

(comment
  (dubstep)
  (dubstep 150 3)
  (dubstep 200 6)
  (dubstep 50 6)
  )

;; we can control the oscillator once it has started
(comment
  (dubstep)
  (ctl dubstep :wobble-freq 4)
  (ctl dubstep :wobble-freq 10)
  (ctl dubstep :wobble-freq 2)  
  )

;; See examples.dubstepbass from the overtone distribution for a much
;; more involved dubstep oscillator!



;; Sometimes it's better to work with notes rather than
;; frequencies. First we can use MIDI notes, where middle C is defined
;; as 60:

(midi->hz 60)
;;=> 261.62556...

;; an octave is 12 semitones, so we can add 12 to the midi note to get
;; the next octave, which doubles the frequency:
(midi->hz 72)
;;=> 523.2511...

;; use it in a synth like this:
(comment
  (dubstep (midi->hz 60))
  )

;; MIDI notes are more abstract than frequencies, but sometimes it's
;; nicer to work with note names. We can use the note function:

(note :c4)
;;=> 60

(comment
  (dubstep (midi->hz (note :c4)))
  )

;; often, rather than doing the conversion inline, we'll translate a
;; list of notes into a list of frequencies which we'll later play:

(def notes (vec (map (comp midi->hz note) [:c3 :g3 :c3])))
;;=> (130.8127826502993 195.99771799087463 130.8127826502993)

(comment
  (dubstep (notes 0))
  (dubstep (notes 1))
  )