/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles.spawner;

import com.stevenlr.waffle.particles.Particle;

public class PointParticleSpawner extends ParticleSpawner {

	private float _x;
	private float _y;

	protected PointParticleSpawner(float x, float y, ParticleSpawner subSpawner) {
		super(subSpawner);
		_x = x;
		_y = y;
	}

	@Override
	public boolean canSpawnParticle() {
		if (_subSpawner != null) {
			return _subSpawner.canSpawnParticle();
		}

		return false;
	}

	@Override
	public boolean isDoneSpawning() {
		if (_subSpawner != null) {
			return _subSpawner.isDoneSpawning();
		}

		return true;
	}

	@Override
	public Particle spawnParticle() {
		if (_subSpawner != null) {
			Particle p = _subSpawner.spawnParticle();

			p.x = _x;
			p.y = _y;

			return p;
		}

		return null;
	}

	@Override
	public void update(float dt) {
		if (_subSpawner != null) {
			_subSpawner.update(dt);
		}
	}
}
