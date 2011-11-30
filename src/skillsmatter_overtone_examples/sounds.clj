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

;; See examples.dubstepbass from the overtone distribution for a much
;; more involved dubstep oscillator!