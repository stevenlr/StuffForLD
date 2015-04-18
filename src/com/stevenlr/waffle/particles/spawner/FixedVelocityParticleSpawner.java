/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.particles.Particle;

public class FixedVelocityParticleSpawner extends ParticleSpawner {

	private float _dx;
	private float _dy;

	public FixedVelocityParticleSpawner(float dx, float dy, ParticleSpawner subSpawner) {
		super(subSpawner);
		_dx = dx;
		_dy = dy;
	}

	@Override
	public Particle spawnParticle() {
		if (_subSpawner != null) {
			Particle p = _subSpawner.spawnParticle();

			p.dx = _dx;
			p.dy = _dy;

			return p;
		}

		return null;
	}
}
